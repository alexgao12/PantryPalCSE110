

import java.io.IOException;

public @interface Test {

    Class<IOException> expected();

}
