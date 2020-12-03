package se.iths.contactdomain;

import se.iths.storage.Storage;
import java.util.ArrayList;

public class ContactBook {

    private static Storage storage = new Storage();
    private ArrayList<Contact> ourContactBook;

    public void loadOurContactBook() {
        ourContactBook = storage.loadFromFile();
    }

    public void saveOurContactBook() {
        storage.writeToFile(ourContactBook);
    }

    public void addContact(Contact newContact) {
        if(findContact(newContact.getFirstName(), newContact.getLastName()) >= 0) {
            System.out.println(newContact.getFirstName() + " is already in your contact book.");
        } else {
            if(ourContactBook.add(newContact)) {
                System.out.println(newContact.getFirstName() + " has now been added to your contact book.");
                saveOurContactBook();
            } else {
                System.out.println("Something went wrong, contact could not be added to your contact book");
            }
        }
    }

    // Kontrollerar För- och Efternamn mot ourContactBook
    private int findContact(String contactFirstName, String contactLastName) {

        for(Contact contact: ourContactBook){
            if(contact.getFirstName().toLowerCase().trim().equals(contactFirstName.toLowerCase().trim()) &&
                    contact.getLastName().toLowerCase().trim().equals(contactLastName.toLowerCase().trim())){
                return ourContactBook.indexOf(contact);
            }
        }
        return -1;
    }

    //@Override ??
    public Contact searchContact(String firstName, String lastName){
        int index = findContact(firstName, lastName);
        if(index >= 0){
            return this.ourContactBook.get(index);
        }
        return null;
    }

    public void printContactBook(){
        if(ourContactBook.size() > 0) {
            System.out.println("Our contacts:");
            for (Contact contact : this.ourContactBook) {
                System.out.println("Name: " + contact.getFirstName() + " " + contact.getLastName());
                System.out.println("Telephone number: " + contact.getTelephone());
            }
        } else {
            System.out.println("Contact book is empty");
        }
    }

    public boolean removeContact(String firstName, String lastName) {
        int contactIndex = findContact(firstName, lastName);
        if(contactIndex >=0) {
            if (ourContactBook.remove(contactIndex) == null) {
                //"Contact couldn't be removed from contact book"
                return false;
            } else {
                storage.writeToFile(ourContactBook);
                //"Contact removed from contact book"
                return true;
            }
        } else {
            //index <0 i.e. "Name not found in contact book"
            return false;
        }
    }
}
