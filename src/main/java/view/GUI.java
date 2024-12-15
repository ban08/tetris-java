package view;

import java.io.IOException;

public interface GUI {
    enum ACTION {
        ARROW_UP, ARROW_DOWN, ARROW_LEFT, ARROW_RIGHT,
        SPACE, A, D, P, S_KEY, E_KEY, R_KEY, Q_KEY, B_KEY,
        NONE, SELECT, ESCAPE
    }

    ACTION getNextAction() throws IOException;
    void clear();
    void refresh() throws IOException;
    void close() throws IOException;
}
