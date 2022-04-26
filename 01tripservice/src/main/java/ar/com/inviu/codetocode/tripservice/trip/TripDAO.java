package ar.com.inviu.codetocode.tripservice.trip;

import ar.com.inviu.codetocode.tripservice.exception.CollaboratorCallException;
import ar.com.inviu.codetocode.tripservice.user.User;
import java.util.List;

public class TripDAO {

	public static List<Trip> findTripsByUser(User user) {
		throw new CollaboratorCallException(
				"TripDAO should not be invoked on an unit test.");
	}
	
}