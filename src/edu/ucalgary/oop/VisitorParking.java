package edu.ucalgary.oop;

import java.time.LocalDate;
import java.util.*;


// Decided it would make sense to make HouseholdParking the parent of VisitorParking.
public class VisitorParking {
     //* Both constructors and both addVisitorReservation() should throw an IllegalArgumentException for an invalid licence as defined in the Parking class.
    // Two different constructors below, one with and one without a LocalDate object.
    // Idea to throw IllegalArgException -> setup a regex expression that models a valid license-plate pattern, use it for verification.
     //
     //
     //
     //


   HashMap<String, TreeSet<LocalDate>> visitorLicences = new HashMap<String, TreeSet<LocalDate>>();

   VisitorParking() {}



    VisitorParking(String licence) throws IllegalArgumentException {
        try{
            licence = Parking.standardizeAndValidateLicence(licence);    // Use the pre-constructed class, 'VisitorParking', in order to validate the inputted license plate.
        } catch(IllegalArgumentException e) {                  // As depicted, we will catch the exception thrown via an event and re-throw it within this method.
            throw new IllegalArgumentException();
        }
        // By default, make the one-input argument, 'licence', have a date of 'today' that covers the next two days....
        LocalDate today = LocalDate.now();
        TreeSet<LocalDate> myTempTree = new TreeSet<LocalDate>();
        myTempTree.add(today);
        visitorLicences.put(licence, myTempTree);

    }

    public void dateChecker(LocalDate date) throws IllegalArgumentException {
       String[] dArray = date.toString().split("-");
       boolean invalid = false;
       if(Integer.parseInt(dArray[0]) < 2023) {invalid = true;}
       if(Integer.parseInt(dArray[1]) > 31 || Integer.parseInt(dArray[1]) < 1 ) {invalid = true;};
       if(Integer.parseInt(dArray[1]) > 12 || Integer.parseInt(dArray[1]) < 1 ) {invalid = true;};
       if (invalid) {
           throw new IllegalArgumentException();
       }
       return;
    }

    VisitorParking(String licence, LocalDate date) throws IllegalArgumentException {
        try{
            licence = Parking.standardizeAndValidateLicence(licence);
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Not a valid licence");
        }
        try {
            dateChecker(date); // implented dateChecker as a helper method.
        } catch(IllegalArgumentException e){    // catches this throw from dateChecker via an event 'e'.
            throw new IllegalArgumentException("Invalid date inputted");
        }
        TreeSet<LocalDate> myTempTree = new TreeSet<LocalDate>();
        myTempTree.add(date);
        visitorLicences.put(licence, myTempTree);
    }

    //private HashMap<String, TreeSet> arrayLicenses = new HashMap<String, TreeSet>();  IMPLEMENT LATER FOR BONUS


    // when a license is registered, it is for the current date plus two additional days.
    public void addVisitorReservation(String licence) throws IllegalArgumentException {
        try {
            licence = Parking.standardizeAndValidateLicence(licence);
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException("Licence is not valid!");
        }
        try{
            int count = 0;
            for(String tempLicence : this.visitorLicences.keySet()) {
                for(LocalDate tempDate : this.visitorLicences.get(tempLicence)) {
                    if( (tempDate.equals(LocalDate.now())) || (tempDate.equals(LocalDate.now().plusDays(1))) || (tempDate.equals(LocalDate.now().plusDays(2))) || (LocalDate.now().equals(tempDate.plusDays(1))) || (LocalDate.now().equals(tempDate.plusDays(2))) )  {
                        count++;
                    }
                    if(count > 1) {
                        throw new IllegalArgumentException();
                    }
                }
            }
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }

        for(String possibleLicence : visitorLicences.keySet()){
            if(Objects.equals(possibleLicence, licence) && this.visitorLicences.get(possibleLicence).contains(LocalDate.now())){
                throw new IllegalArgumentException("Licence already in the system, and that licence's date is today, so this wont work.");
            }
        }
        LocalDate today = LocalDate.now();
        if(this.visitorLicences.containsKey(licence)) {
            this.visitorLicences.get(licence).add(today);
        } else {
            TreeSet<LocalDate> temp = new TreeSet<LocalDate>();
            temp.add(today);
            this.visitorLicences.put(licence, temp);
        }

    }
    public void addVisitorReservation(String licence, LocalDate date) throws IllegalArgumentException {
        try {
            licence = Parking.standardizeAndValidateLicence(licence);
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
        try {
            dateChecker(date); // implented dateChecker as a helper method.
        } catch(IllegalArgumentException e){
            throw new IllegalArgumentException();
        }
        try{
            for(String tempLicence : this.visitorLicences.keySet()) {
                if(Objects.equals(tempLicence, licence)) {
                    for(LocalDate temp : this.visitorLicences.get(tempLicence)) {
                        if(date.equals(temp) || date.equals(temp.plusDays(1)) || date.equals(temp.plusDays(2)) || temp.equals(date.plusDays(1)) || temp.equals(date.plusDays(2))) {
                            throw new IllegalArgumentException();
                        }
                    }
                }
            }
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }

        try{
            int count = 0;
            for(String tempLicence : this.visitorLicences.keySet()) {
                for(LocalDate tempDate : this.visitorLicences.get(tempLicence)) {
                    if( (tempDate.equals(date)) || (tempDate.equals(date.plusDays(1))) || (tempDate.equals(date.plusDays(2))) || (date.equals(tempDate.plusDays(1))) || (date.equals(tempDate.plusDays(2))) )  {
                        count++;
                    }
                    if(count > 1) {
                        throw new IllegalArgumentException();
                    }
                }
            }
        } catch(IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }

        if(this.visitorLicences.containsKey(licence)) {
            this.visitorLicences.get(licence).add(date);
        } else {
            TreeSet<LocalDate> temp = new TreeSet<LocalDate>();
            temp.add(date);
            this.visitorLicences.put(licence, temp);
        }
    }

    public boolean licenceIsRegisteredForDate(String licenceToCheck, LocalDate date) {
       boolean store = true;
       licenceToCheck = Parking.standardizeAndValidateLicence(licenceToCheck);
       try{
           if( ( (!this.visitorLicences.get(licenceToCheck).contains(date)) && (!this.visitorLicences.get(licenceToCheck).contains(date.minusDays(1))) && (!this.visitorLicences.get(licenceToCheck).contains(date.minusDays(2))) ) ){
               store = false;
           }
           //if(this.visitorLicences.get(licenceToCheck).isEmpty()) {return false;}
           /*
           for(LocalDate dateObj : this.visitorLicences.get(licenceToCheck)) {
               if( (date.equals(dateObj)) || (date.equals(dateObj.plusDays(1))) || (date.equals(dateObj.plusDays(2))) || (date.equals(dateObj.minusDays(2))) || (date.equals(dateObj.minusDays(1))) ) {
                   store = true;
               }
           }

            */
       } catch(NullPointerException e){
           return false;
       }
       return store;
    }

    public boolean licenceIsRegisteredForDate(String licenceToCheck) {
        licenceToCheck = Parking.standardizeAndValidateLicence(licenceToCheck);
        try{
            if(this.visitorLicences.get(licenceToCheck).isEmpty()) {return false;}
            for(LocalDate dateObj : this.visitorLicences.get(licenceToCheck)) {
                if( (LocalDate.now().equals(dateObj)) || (LocalDate.now().equals(dateObj.plusDays(1))) || (LocalDate.now().equals(dateObj.plusDays(2))) || (LocalDate.now().equals(dateObj.minusDays(2))) || (LocalDate.now().equals(dateObj.minusDays(1))) ) {
                    return true;
                }
            }
        } catch(NullPointerException e){
            return false;
        }
        return false;
       /*
       try {
           for(LocalDate possibleDate : this.visitorLicences.get(licenceToCheck)) {
               if(Objects.equals(possibleDate, LocalDate.now()) || Objects.equals(possibleDate, LocalDate.now().plusDays(1)) || Objects.equals(possibleDate, LocalDate.now().plusDays(2)) || Objects.equals(possibleDate.plusDays(1), LocalDate.now())
               || Objects.equals(possibleDate.plusDays(2), LocalDate.now())) {
                   return true;
               }
           }
       } catch(NullPointerException e) {
           return false;
       }
        return false;

        */
    }

    public ArrayList<String> getLicencesRegisteredForDate() {
       LocalDate today = LocalDate.now();                                       // use this to fetch all licences for today.
       ArrayList<String> licencesForToday = new ArrayList<String>();
       for(String tempLicenceKey : this.visitorLicences.keySet()) {
           if(this.visitorLicences.get(tempLicenceKey).contains(today)){
               licencesForToday.add(tempLicenceKey);
           }
       }
       return licencesForToday;
    }

    public ArrayList<String> getLicencesRegisteredForDate(LocalDate date) {
       ArrayList<String> licencesForDate = new ArrayList<String>();
       for(String tempLicenceKey : this.visitorLicences.keySet()) {
           for (LocalDate dateObject : this.visitorLicences.get(tempLicenceKey)) {
               if (dateObject.equals(date) || dateObject.equals(date.plusDays(1)) || dateObject.equals(date.plusDays(2)) || dateObject.equals(date.minusDays(2))
                       || dateObject.equals(date.minusDays(1))) {
                   licencesForDate.add(tempLicenceKey);
               }
           }
       }

       return licencesForDate;
    }


    public ArrayList<LocalDate> getAllDaysLicenceIsRegistered(String licence) {
       licence = Parking.standardizeAndValidateLicence(licence);
       ArrayList<LocalDate> allDates = new ArrayList<LocalDate>();
       for(LocalDate tmpDate : this.visitorLicences.get(licence)) {
           while(!allDates.contains(tmpDate)){
               allDates.add(tmpDate);
               allDates.add(tmpDate.plusDays(1));
               allDates.add(tmpDate.plusDays(2));
           }
       }
       allDates.sort(Comparator.naturalOrder());
       return allDates;
    }

    public ArrayList<LocalDate> getStartDaysLicenceIsRegistered(String licence) {
        licence = Parking.standardizeAndValidateLicence(licence);
        ArrayList<LocalDate> tempList = new ArrayList<LocalDate>();
        /*
        for(String possibleLicence : this.visitorLicences.keySet()) {
            if(licence==possibleLicence) {
                for(LocalDate date : this.visitorLicences.get(possibleLicence)){
                    if(!tempList.contains(date)) {
                        tempList.add(date);
                    }
                }
            }
        }
        */
        for(LocalDate date : this.visitorLicences.get(licence)) {
            if(!tempList.contains(date)) {
                tempList.add(date);
            }
        }
        tempList.sort(Comparator.naturalOrder());
        return tempList;
    }


}



