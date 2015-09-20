$(document).ready(function() {
	$("#layout_search_keyword").on("keypress", function() {
		if ($(this).val() != '') {
			$("#layout_search_head").removeClass("on-blur");
			$("#layout_search_head").addClass("on-focus");

			$("#layout_search_submit").show();
		}
	});
	$("#layout_search_keyword").on("blur", function() {
		if ($(this).val() == '') {
			$("#layout_search_head").addClass("on-blur");
			$("#layout_search_head").removeClass("on-focus");

			$("#layout_search_submit").hide();
		}
	});

	$("#layout_search_bar_cancel").on("click", function() {
		$("#layout_search_keyword").val("");

		$("#layout_search_head").addClass("on-blur");
		$("#layout_search_head").removeClass("on-focus");

		$("#layout_search_submit").hide();

		$("#layout_searchForm").submit();
	});

	$("#layout_search_submit").on("click", function() {
		$("#layout_searchForm").submit();
	});
});