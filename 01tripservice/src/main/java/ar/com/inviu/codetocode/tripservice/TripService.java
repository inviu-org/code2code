package ar.com.inviu.codetocode.tripservice;

import ar.com.inviu.codetocode.tripservice.exception.UserNotLoggedInException;
import ar.com.inviu.codetocode.tripservice.trip.Trip;
import ar.com.inviu.codetocode.tripservice.trip.TripDAO;
import ar.com.inviu.codetocode.tripservice.user.User;
import ar.com.inviu.codetocode.tripservice.user.UserSession;
import java.util.ArrayList;
import java.util.List;

public class TripService {
  public List<Trip> getTripsByUser(User user) throws UserNotLoggedInException {
    List<Trip> tripList = new ArrayList<>();
    User loggedUser = getLoggedUser();
    boolean isFriend = false;
    if (loggedUser != null) {
      for (User friend : user.getFriends()) {
        if (friend.equals(loggedUser)) {
          isFriend = true;
          break;
        }
      }
      if (isFriend) {
        tripList = findTripsByUser(user);
      }
      return tripList;
    } else {
      throw new UserNotLoggedInException();
    }
  }

  protected List<Trip> findTripsByUser(User user) {
    return TripDAO.findTripsByUser(user);
  }

  protected User getLoggedUser() {
    return UserSession.getInstance().getLoggedUser();
  }
}

