package handlers;

/*
    Defines an interface for classes that are
    able to display a menu and handle input from
    the user
*/

public interface Handler {
    public void handle();
    public String getDescription();
}
