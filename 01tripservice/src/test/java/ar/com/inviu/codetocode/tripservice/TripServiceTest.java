package ar.com.inviu.codetocode.tripservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import ar.com.inviu.codetocode.tripservice.exception.UserNotLoggedInException;
import ar.com.inviu.codetocode.tripservice.trip.Trip;
import ar.com.inviu.codetocode.tripservice.user.User;
import java.util.List;
import org.junit.jupiter.api.Test;

class TripServiceTest {

  User loggedUser;

  User GUEST = null;

  User WODA = new User();
  User ENZO = new User();
  User TAPIA = new User();

  Trip LONDON = new Trip();

  TripService tripService = new TesteableTripService();

  @Test
  void shouldThrowExceptionWhenUserIsNotLoggedIn() {
    loggedUser = GUEST;

    assertThatThrownBy(() -> tripService.getTripsByUser(null))
        .isInstanceOf(UserNotLoggedInException.class);
  }

  @Test
  void shouldReturnEmptyTripListWhenUserHasNoFriends(){
    loggedUser = WODA;
    User userWithNoFriends = new User();

    assertThat(tripService.getTripsByUser(userWithNoFriends))
        .isEmpty();
  }

  @Test
  void shouldReturnEmptyTripListWhenUserIsNoFriendsWithLogged(){
    loggedUser = WODA;

    ENZO.addFriend(TAPIA);

    assertThat(tripService.getTripsByUser(ENZO))
        .isEmpty();
  }

  @Test
  void shouldReturnTripListWhenUserIsFriendsWithLogged(){
    loggedUser = WODA;

    ENZO.addFriend(TAPIA);
    ENZO.addFriend(WODA);
    ENZO.addTrip(LONDON);

    assertThat(tripService.getTripsByUser(ENZO))
        .containsExactly(LONDON);
  }

  class TesteableTripService extends TripService {

    @Override
    protected List<Trip> findTripsByUser(User user) {
      return user.getTrips();
    }

    @Override
    protected User getLoggedUser() {
      return loggedUser;
    }
  }
}
