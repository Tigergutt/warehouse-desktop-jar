package se.melsom.warehouse.command;

import se.melsom.warehouse.application.Command;
import se.melsom.warehouse.presentation.logger.LoggerController;

public class ShowLoggerView extends Command {
    LoggerController controller;

    public ShowLoggerView(LoggerController loggerController) {
		this.controller = loggerController;
	}

	@Override
	public void execute() {
		controller.showView();
	}

}
