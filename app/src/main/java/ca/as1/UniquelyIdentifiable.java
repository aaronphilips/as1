package ca.as1;

import java.util.UUID;

/**
 * Created by Aaron Philips on 9/28/2016.
 * small interface to be uniquely identifiable
 * so i wouldn't have to pass entire objects between habits, just ids, then use the save file
 */
public interface UniquelyIdentifiable {
    public UUID getID();
}
