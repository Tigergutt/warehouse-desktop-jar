package se.melsom.warehouse.command;

import se.melsom.warehouse.presentation.logger.LoggerController;

/**
 * The Show logger view command.
 */
public class ShowLoggerView extends Command {
    /**
     * The Controller.
     */
    LoggerController controller;

    /**
     * Instantiates a new Show logger view.
     *
     * @param loggerController the logger controller
     */
    public ShowLoggerView(LoggerController loggerController) {
		this.controller = loggerController;
	}

	@Override
	public void execute() {
		controller.showView();
	}

}
