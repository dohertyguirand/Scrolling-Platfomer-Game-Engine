package ooga.data;

import ooga.OogaDataException;
import ooga.view.OogaProfile;

import java.util.List;

public interface ProfileReaderExternal {

  /**
   * Adds a given profile to the profile folder
   * @param newProfile the profile to add to the saved profile folder
   */
  void addNewProfile(OogaProfile newProfile);

  /**
   * @return A list of Profiles according to the data stored in the Users folder. Returns an empty list if there are no
   * existing profiles
   */
  List<OogaProfile> getProfiles() throws OogaDataException;
}
