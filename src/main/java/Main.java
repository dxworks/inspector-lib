import commands.AgeCommand;
import commands.InspectorLibCommand;

import static commands.InspectorLibCommand.AGE;

public class Main {

    public static void main(String[] args) {

        if (args.length == 0) {
            System.err.println("No command found");
            return;
        }

        String command = args[0];

        InspectorLibCommand inspectorLibCommand = getInspectorLibCommand(command);

        if (inspectorLibCommand == null) {
            System.err.println("Invalid command!");
            return;
        }

        boolean isValidInput = inspectorLibCommand.parse(args);

        if (!isValidInput) {
            System.err.println("Input is not valid!");
            return;
        }

        inspectorLibCommand.execute(args);
    }

    private static InspectorLibCommand getInspectorLibCommand(String command) {
        switch (command) {
            case AGE:
                return new AgeCommand();
            default:
                return null;
        }
    }
}
