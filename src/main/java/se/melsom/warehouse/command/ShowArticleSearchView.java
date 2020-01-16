package se.melsom.warehouse.command;

import se.melsom.warehouse.presentation.search.SearchController;

public class ShowArticleSearchView extends Command {
	SearchController equipmentSearchViewModel;

	public ShowArticleSearchView(SearchController equipmentSearchViewModel) {
		this.equipmentSearchViewModel = equipmentSearchViewModel;
	}

	@Override
	public void execute() {
		equipmentSearchViewModel.showView();
	}

}
