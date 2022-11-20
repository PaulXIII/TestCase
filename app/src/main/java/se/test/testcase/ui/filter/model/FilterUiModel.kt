package se.test.testcase.ui.filter.model

data class FilterUiModel(
    val id: String,
    val name: String,
    val imageUrl: String,
    val isSelected: Boolean = false,
)