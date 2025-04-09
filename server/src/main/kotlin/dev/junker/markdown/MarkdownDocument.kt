package dev.junker.markdown

import dev.junker.classSelector
import dev.junker.components.general.imageBanner
import kotlinx.html.*
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.getTextInNode
import org.intellij.markdown.flavours.gfm.GFMElementTypes
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMTokenTypes
import org.intellij.markdown.parser.MarkdownParser

val exclamation = "exclamation".classSelector()

data class MarkdownDocument(
    val content: FlowContent.() -> Unit
)

fun markdownDocument(metadata: MarkdownMetadata, markdown: String): MarkdownDocument {
    return MarkdownDocument(
        content = {
            em {
                if (metadata.modifiedDate != null) {
                    time {
                        dateTime = metadata.modifiedDate
                        +metadata.modifiedDate
                    }
                    span {
                        title = "Edited"
                        +"*"
                    }
                } else if (metadata.creationDate != null) {
                    time {
                        dateTime = metadata.creationDate
                        +metadata.creationDate
                    }
                }
            }

            renderMarkdown(markdown)
        }
    )
}

fun FlowContent.renderMarkdown(markdown: String) {
    val flavor = GFMFlavourDescriptor()
    val parser = MarkdownParser(flavor)
    val parsedTree = parser.buildMarkdownTreeFromString(markdown)
    renderMarkdown(parsedTree, markdown)
}

// ====================================================================================================================

private enum class EolMode {
    NONE,
    SPACE,
    PRESERVE
}

private fun FlowContent.renderMarkdown(
    node: ASTNode,
    markdown: String,
    eolMode: EolMode = EolMode.NONE
) {
    when (node.type) {
        MarkdownElementTypes.ATX_1 -> {
            h1 { renderChildNodes(node, markdown) }
        }
        MarkdownElementTypes.ATX_2 -> {
            h2 { renderChildNodes(node, markdown) }
        }
        MarkdownElementTypes.ATX_3 -> {
            span(classes = exclamation.className) { renderChildNodes(node, markdown) }
        }
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
        // Only render paragraphs when not part of a list item
        MarkdownElementTypes.PARAGRAPH -> {
            when (this) {
                !is LI -> {
                    p { renderChildNodes(node, markdown, EolMode.SPACE) }
                }
                else -> {
                    renderChildNodes(node, markdown, EolMode.SPACE)
                }
            }
        }
        MarkdownElementTypes.EMPH -> {
            em { renderChildNodes(node, markdown, EolMode.SPACE) }
        }
        MarkdownElementTypes.STRONG -> {
            strong { renderChildNodes(node, markdown) }
        }
        GFMElementTypes.STRIKETHROUGH -> {
            s { renderChildNodes(node, markdown) }
        }
        MarkdownElementTypes.UNORDERED_LIST -> {
            ul { renderChildNodes(node, markdown) }
        }
        MarkdownElementTypes.ORDERED_LIST -> {
            ol { renderChildNodes(node, markdown) }
        }
        MarkdownElementTypes.LIST_ITEM -> {
            when (this) {
                is UL -> {
                    li { renderChildNodes(node, markdown) }
                }
                is OL -> {
                    li { renderChildNodes(node, markdown) }
                }
                else -> {
                    renderChildNodes(node, markdown)
                }
            }
        }
        MarkdownElementTypes.IMAGE -> {
            span (classes = imageBanner.className) {
                img {
                    node.children
                        .filter { it.type != MarkdownTokenTypes.EXCLAMATION_MARK }
                        .let { renderChildNodes(it, markdown, eolMode) }
                }
            }
        }
        MarkdownElementTypes.INLINE_LINK -> {
            val href = node.children
                .find { it.type == MarkdownElementTypes.LINK_DESTINATION }
                ?.getTextInNode(markdown)?.toString() ?: "#"
            val text = node.children
                .find { it.type == MarkdownElementTypes.LINK_TEXT }
            val titleText = node.children
                .find { it.type == MarkdownElementTypes.LINK_TITLE }
                ?.getTextInNode(markdown)?.toString()

            if (this is IMG) {
                src = href
                alt = text?.getTextInNode(markdown)?.toString() ?: ""
                if (titleText != null) {
                    title = titleText
                }
            } else {
                text?.also {
                    a(href = href) {
                        if (titleText != null) {
                            title = titleText
                        }
                        renderChildNodes(it, markdown)
                    }
                }
            }
        }
        // No need to support code blocks, only fences
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

                code { renderChildNodes(firstEolRemoved, markdown, EolMode.PRESERVE) }
            }
        }
        MarkdownTokenTypes.CODE_FENCE_CONTENT -> {
            +node.getTextInNode(markdown).trimEnd().toString()
        }
        MarkdownElementTypes.CODE_SPAN -> {
            code { renderChildNodes(node, markdown) }
        }
        MarkdownElementTypes.BLOCK_QUOTE -> {
            blockQuote { renderChildNodes(node, markdown) }
        }
        // Render auto-links as text
        GFMTokenTypes.GFM_AUTOLINK,
        MarkdownTokenTypes.TEXT -> {
            val nodeText = node
                .getTextInNode(markdown)
                .trim().toString()

            when (nodeText) {
                "..." -> hr {  }
                else -> +nodeText
            }
        }
        MarkdownTokenTypes.EOL -> {
            when (eolMode) {
                EolMode.NONE -> { /* Ignore */ }
                EolMode.SPACE -> +" "
                EolMode.PRESERVE -> +"\n"
            }
        }
        MarkdownTokenTypes.WHITE_SPACE -> {
            +" "
        }
        MarkdownTokenTypes.COLON -> {
            +":"
        }
        MarkdownTokenTypes.SINGLE_QUOTE -> {
            +"'"
        }
        MarkdownTokenTypes.DOUBLE_QUOTE -> {
            +"\""
        }
        MarkdownTokenTypes.EXCLAMATION_MARK -> {
            +"!"
        }
        MarkdownTokenTypes.LPAREN -> {
            +"("
        }
        MarkdownTokenTypes.RPAREN -> {
            +")"
        }
        else -> {
            renderChildNodes(node, markdown)
        }
    }
}

private fun FlowContent.renderChildNodes(
    parentNode: ASTNode,
    markdown: String,
    eolMode: EolMode = EolMode.NONE
) {
    renderChildNodes(parentNode.children, markdown, eolMode)
}

private fun FlowContent.renderChildNodes(
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
                section { renderAllNodes(section, markdown, eolMode) }
            else -> renderAllNodes(section, markdown, eolMode)
        }
    }
}

private fun FlowContent.renderAllNodes(
    nodes: List<ASTNode>,
    markdown: String,
    eolMode: EolMode = EolMode.NONE
) {
    nodes.forEach { node ->
        renderMarkdown(node, markdown, eolMode)
    }
}
