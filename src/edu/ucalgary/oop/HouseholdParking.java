/*
Copyright Ann Barcomb and Emily Marasco, 2021-2023
Licensed under GPL v3
See LICENSE.txt for more information.
*/

package edu.ucalgary.oop;
import java.time.LocalDate;
import java.util.*;
import edu.ucalgary.oop.VisitorParking;

public class HouseholdParking extends CalgaryProperty {
    // Each residential property is allowed one street parking permit
    public VisitorParking newVisitorObject = new VisitorParking();
    private LinkedList<String> residentLicence = new LinkedList<String>();
    private int maxLicences = 3;

    public HouseholdParking(int taxRollNumber, String zoning, String streetName, int buildingNumber, String postCode, String buildingAnnex) throws IllegalArgumentException {
        super(taxRollNumber,zoning,streetName,buildingNumber,postCode,buildingAnnex);
    }

    public HouseholdParking(int taxRollNumber, String zoning, String streetName, int buildingNumber, String postCode) throws IllegalArgumentException {
        super(taxRollNumber,zoning,streetName,buildingNumber,postCode);
    }

    /*
     * Add a licence to the first empty spot in residentLicence, or replace the most recent
     * Ignore if the licence is already stored
     * @param licence - The licence plate to be added
     * @throws IllegalArgumentException if licence plate isn't a valid Alberta licence
     */
    public void addOrReplaceResidentLicence(String licence) throws IllegalArgumentException {
        licence = Parking.standardizeAndValidateLicence(licence);
        int len = residentLicence.size();

        // If licence is already in the list, don't continue
        for (String val : residentLicence) {
            if (val.equals(licence)) {
                return;
            }
        }

        // If fewer than three are stored, add to list
        if (len < maxLicences) {
            residentLicence.add(licence);

            // If more than three are stored, replace last in list
        } else {
            this.residentLicence.set(maxLicences-1, licence);
        }
    }

    /*
     * Remove all listed licences
     * @return whether the operation succeeded or not
     */
    public boolean removeResidentLicence() {
        this.residentLicence.clear();
        return true;
    }



    /*
     * Remove a specific listed licence
     * @param licence - the licence to be removed
     * @return whether the operation succeeded or not
     */
    public boolean removeResidentLicence(String licence) {
        // Standardize the licence name. If it is invalid, it can't exist so return false.
        try {
            licence = Parking.standardizeAndValidateLicence(licence);
        }
        catch (Exception e) {
            return false;
        }

        for (int i=0; i < this.residentLicence.size(); i++) {
            if (licence.equals(this.residentLicence.get(i))) {
                this.residentLicence.remove(i);
                return true;
            }
        }

        // Couldn't find entry
        return false;
    }

    /*
     * Get all the licences stored for the resident
     * @return An array containing the resident's licences
     */


    //Below was the old method used in the older version of this program.
    public String getResidentLicence() {
        try {
            String storedResLicence = this.residentLicence.getFirst();
            for(String resLicence : this.residentLicence) {
                if(resLicence==null) {
                    return storedResLicence;
                }
                else{
                    storedResLicence = resLicence;
                }
            }
            return storedResLicence;
        } catch(NoSuchElementException e) {
            return "";
        }
    }




    public ArrayList<String> getLicencesRegisteredForDate() {
        return this.newVisitorObject.getLicencesRegisteredForDate();
    }
    public ArrayList<String> getLicencesRegisteredForDate(LocalDate commonDate) {
        return this.newVisitorObject.getLicencesRegisteredForDate(commonDate);
    }

    public boolean licenceIsRegisteredForDate(String licence) {
        return this.newVisitorObject.licenceIsRegisteredForDate(licence);
    }
    public ArrayList<LocalDate> getAllDaysLicenceIsRegistered(String licence1) {
        return this.newVisitorObject.getAllDaysLicenceIsRegistered(licence1);
    }


    public boolean licenceIsRegisteredForDate(String license, LocalDate date) {
        return this.newVisitorObject.licenceIsRegisteredForDate(license, date);
    }

    public ArrayList<LocalDate> getStartDaysLicenceIsRegistered(String licence) {
        return this.newVisitorObject.getStartDaysLicenceIsRegistered(licence);
    }

    public void addVisitorReservation(String licence) {
        this.newVisitorObject.addVisitorReservation(licence);
    }

    public void addVisitorReservation(String licence, LocalDate date) {
        this.newVisitorObject.addVisitorReservation(licence, date);
    }

    public VisitorParking getVisitors() {
        return this.newVisitorObject;
    }


}

