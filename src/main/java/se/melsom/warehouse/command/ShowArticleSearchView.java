package se.melsom.warehouse.command;

import se.melsom.warehouse.presentation.search.SearchController;

/**
 * The Show article search view command.
 */
public class ShowArticleSearchView extends Command {
    /**
     * The Equipment search view model.
     */
    SearchController equipmentSearchViewModel;

    /**
     * Instantiates a new Show article search view.
     *
     * @param equipmentSearchViewModel the equipment search view model
     */
    public ShowArticleSearchView(SearchController equipmentSearchViewModel) {
		this.equipmentSearchViewModel = equipmentSearchViewModel;
	}

	@Override
	public void execute() {
		equipmentSearchViewModel.showView();
	}

}
