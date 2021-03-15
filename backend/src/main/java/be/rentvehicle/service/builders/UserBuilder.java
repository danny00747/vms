package be.rentvehicle.service.builders;

import be.rentvehicle.domain.Address;

public class UserBuilder {

    public static class AddressBuilder {

        private final Address address = new Address();

        public AddressBuilder road(String road) {
            address.setRoad(road);
            return this;
        }

        public AddressBuilder houseNumber(int houseNumber) {
            address.setHouseNumber(houseNumber);
            return this;
        }

        public AddressBuilder in(Integer postBox) {
            address.setPostBox(postBox);
            return this;
        }
    }

    // create person (User/with) thatLives at (address/road) with postcode in (Town)
}
