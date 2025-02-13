package dev.junker.markdown

import kotlinx.html.*
import org.intellij.markdown.MarkdownElementTypes
import org.intellij.markdown.MarkdownTokenTypes
import org.intellij.markdown.ast.ASTNode
import org.intellij.markdown.ast.CompositeASTNode
import org.intellij.markdown.ast.getTextInNode
import org.intellij.markdown.flavours.gfm.GFMElementTypes
import org.intellij.markdown.flavours.gfm.GFMFlavourDescriptor
import org.intellij.markdown.flavours.gfm.GFMTokenTypes
import org.intellij.markdown.parser.MarkdownParser

data class MarkdownDocument(
    val content: FlowContent.() -> Unit
)

fun markdownDocument(markdown: String): MarkdownDocument {
    val flavor = GFMFlavourDescriptor()
    val parsedTree = MarkdownParser(flavor).buildMarkdownTreeFromString(markdown)

    return MarkdownDocument(
        content = { renderMarkdown(parsedTree, markdown) }
    )
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
        MarkdownElementTypes.ATX_1 ->
            h1 { renderChildren(node, markdown) }
        MarkdownElementTypes.ATX_2 ->
            h2 { renderChildren(node, markdown) }
        // Only render paragraphs when not part of a list item
        MarkdownElementTypes.PARAGRAPH -> when (this) {
            !is LI ->
                p { renderChildren(node, markdown, EolMode.SPACE) }
            else -> renderChildren(node, markdown)
        }
        MarkdownElementTypes.EMPH ->
            em { renderChildren(node, markdown) }
        MarkdownElementTypes.STRONG ->
            strong { renderChildren(node, markdown) }
        GFMElementTypes.STRIKETHROUGH ->
            s { renderChildren(node, markdown) }
        MarkdownElementTypes.UNORDERED_LIST ->
            ul { renderChildren(node, markdown) }
        MarkdownElementTypes.ORDERED_LIST ->
            ol { renderChildren(node, markdown) }
        MarkdownElementTypes.LIST_ITEM -> when (this) {
            is UL ->
                li { renderChildren(node, markdown) }
            is OL ->
                li { renderChildren(node, markdown) }
            else -> renderChildren(node, markdown)
        }
        MarkdownElementTypes.INLINE_LINK -> {
            val href = node.children
                .find { it.type == MarkdownElementTypes.LINK_DESTINATION }
                ?.getTextInNode(markdown)
                ?.toString() ?: "#"

            node.children
                .find { it.type == MarkdownElementTypes.LINK_TEXT }
                ?.also {
                    a(href = href) { renderChildren(it, markdown) }
                }
        }
        MarkdownElementTypes.CODE_SPAN ->
            code { renderChildren(node, markdown) }
        // No need to support code blocks
        MarkdownElementTypes.CODE_FENCE -> {
            var firstEolSeen = false
            val firstEolRemoved = node.children.filter {
                if (!firstEolSeen && it.type == MarkdownTokenTypes.EOL) {
                    firstEolSeen = true
                    false
                } else true
            }
            val updatedNode = CompositeASTNode(node.type, firstEolRemoved)

            pre {
                node.children
                    .find { it.type == MarkdownTokenTypes.FENCE_LANG }
                    ?.also {
                        val progLang = it.getTextInNode(markdown).toString()
                        // TODO: Actually use this
                        attributes["data-progLang"] = progLang
                    }

                code { renderChildren(updatedNode, markdown, EolMode.PRESERVE) }
            }
        }
        else -> renderChildren(node, markdown)
    }
}

private fun FlowContent.renderChildren(
    node: ASTNode,
    markdown: String,
    eolMode: EolMode = EolMode.NONE
) {
    node.children.forEach {
        renderMarkdown(it, markdown, eolMode)
    }
}
