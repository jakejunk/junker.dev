package dev.junker.markdown

import kotlinx.html.*
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.getTextInNode
import org.intellij.markdown.flavours.gfm.GFMElementTypes
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMTokenTypes
import org.intellij.markdown.parser.MarkdownParser

data class MarkdownDocument(
    val content: FlowContent.() -> Unit
)

fun markdownDocument(metadata: MarkdownMetadata, markdown: String): MarkdownDocument {
    val flavor = GFMFlavourDescriptor()
    val parsedTree = MarkdownParser(flavor).buildMarkdownTreeFromString(markdown)

    return MarkdownDocument(
        content = { renderMarkdown(metadata, parsedTree, markdown) }
    )
}

// ====================================================================================================================

private enum class EolMode {
    NONE,
    SPACE,
    PRESERVE
}

private fun FlowContent.renderMarkdown(
    metadata: MarkdownMetadata,
    node: ASTNode,
    markdown: String,
    eolMode: EolMode = EolMode.NONE
) {
    when (node.type) {
        MarkdownTokenTypes.ATX_CONTENT -> {
            val trimmed = node.children
                .dropWhile { it.type == MarkdownTokenTypes.WHITE_SPACE }
                .dropLastWhile { it.type == MarkdownTokenTypes.WHITE_SPACE }

            if (trimmed.isNotEmpty()) {
                val startOffset = trimmed.first().startOffset
                val endOffset = trimmed.last().endOffset
                val trimmedText = markdown.subSequence(startOffset, endOffset).trim().toString()

                +trimmedText
            }
        }
        MarkdownTokenTypes.CODE_FENCE_CONTENT ->
            +node.getTextInNode(markdown).trimEnd().toString()
        // Don't render auto-links as actual links
        GFMTokenTypes.GFM_AUTOLINK,
        MarkdownTokenTypes.TEXT ->
            +node.getTextInNode(markdown).trim().toString()
        MarkdownTokenTypes.WHITE_SPACE ->
            +" "
        MarkdownTokenTypes.EOL -> when (eolMode) {
            EolMode.NONE -> { /* Ignore */ }
            EolMode.SPACE -> +" "
            EolMode.PRESERVE -> +"\n"
        }
        MarkdownTokenTypes.COLON ->
            +":"
        MarkdownTokenTypes.SINGLE_QUOTE ->
            +"'"
        MarkdownTokenTypes.DOUBLE_QUOTE ->
            +"\""
        MarkdownTokenTypes.EXCLAMATION_MARK ->
            +"!"
        MarkdownTokenTypes.LPAREN ->
            +"("
        MarkdownTokenTypes.RPAREN ->
            +")"
        MarkdownElementTypes.ATX_1 -> {
            h1 { renderChildNodes(metadata, node, markdown) }
            if (metadata.modifiedDate != null) {
                em { +"Edited: ${metadata.modifiedDate}" }
            } else if (metadata.creationDate != null) {
                em { +"Written: ${metadata.creationDate}" }
            }
        }
        MarkdownElementTypes.ATX_2 ->
            h2 { renderChildNodes(metadata, node, markdown) }
        // Only render paragraphs when not part of a list item
        MarkdownElementTypes.PARAGRAPH -> when (this) {
            !is LI ->
                p { renderChildNodes(metadata, node, markdown, EolMode.SPACE) }
            else -> renderChildNodes(metadata, node, markdown)
        }
        MarkdownElementTypes.EMPH ->
            em { renderChildNodes(metadata, node, markdown) }
        MarkdownElementTypes.STRONG ->
            strong { renderChildNodes(metadata, node, markdown) }
        GFMElementTypes.STRIKETHROUGH ->
            s { renderChildNodes(metadata, node, markdown) }
        MarkdownElementTypes.UNORDERED_LIST ->
            ul { renderChildNodes(metadata, node, markdown) }
        MarkdownElementTypes.ORDERED_LIST ->
            ol { renderChildNodes(metadata, node, markdown) }
        MarkdownElementTypes.LIST_ITEM -> when (this) {
            is UL ->
                li { renderChildNodes(metadata, node, markdown) }
            is OL ->
                li { renderChildNodes(metadata, node, markdown) }
            else -> renderChildNodes(metadata, node, markdown)
        }
        MarkdownElementTypes.INLINE_LINK -> {
            val href = node.children
                .find { it.type == MarkdownElementTypes.LINK_DESTINATION }
                ?.getTextInNode(markdown)
                ?.toString() ?: "#"

            node.children
                .find { it.type == MarkdownElementTypes.LINK_TEXT }
                ?.also {
                    a(href = href) { renderChildNodes(metadata, it, markdown) }
                }
        }
        MarkdownElementTypes.CODE_SPAN ->
            code { renderChildNodes(metadata, node, markdown) }
        // No need to support code blocks
        MarkdownElementTypes.CODE_FENCE -> {
            var firstEolSeen = false
            val firstEolRemoved = node.children.filter {
                if (!firstEolSeen && it.type == MarkdownTokenTypes.EOL) {
                    firstEolSeen = true
                    false
                } else true
            }

            pre {
                node.children
                    .find { it.type == MarkdownTokenTypes.FENCE_LANG }
                    ?.also {
                        val progLang = it.getTextInNode(markdown).toString()
                        // TODO: Actually use this
                        attributes["data-progLang"] = progLang
                    }

                code { renderChildNodes(metadata, firstEolRemoved, markdown, EolMode.PRESERVE) }
            }
        }
        else -> renderChildNodes(metadata, node, markdown)
    }
}

private fun FlowContent.renderChildNodes(
    metadata: MarkdownMetadata,
    parentNode: ASTNode,
    markdown: String,
    eolMode: EolMode = EolMode.NONE
) {
    renderChildNodes(metadata, parentNode.children, markdown, eolMode)
}

private fun FlowContent.renderChildNodes(
    metadata: MarkdownMetadata,
    nodes: List<ASTNode>,
    markdown: String,
    eolMode: EolMode
) {
    var currentSection = mutableListOf<ASTNode>()
    var headerEncountered = false
    val sections = buildList {
        nodes.forEach { childNode ->
            val nodeType = childNode.type
            if (nodeType == MarkdownElementTypes.ATX_1 || nodeType == MarkdownElementTypes.ATX_2) {
                headerEncountered = true
                if (currentSection.isNotEmpty()) {
                    add(currentSection)
                }

                currentSection = mutableListOf()
            }

            currentSection += childNode
        }

        if (currentSection.isNotEmpty()) {
            add(currentSection)
        }
    }

    sections.forEach { section ->
        when {
            headerEncountered ->
                section { renderAllNodes(metadata, section, markdown, eolMode) }
            else -> renderAllNodes(metadata, section, markdown, eolMode)
        }
    }
}

private fun FlowContent.renderAllNodes(
    metadata: MarkdownMetadata,
    nodes: List<ASTNode>,
    markdown: String,
    eolMode: EolMode = EolMode.NONE
) {
    nodes.forEach { node ->
        renderMarkdown(metadata, node, markdown, eolMode)
    }
}
