package states;

import view.GUI;
import java.io.IOException;

public abstract class State {
    public abstract State step(GUI gui, long time) throws IOException;
}
