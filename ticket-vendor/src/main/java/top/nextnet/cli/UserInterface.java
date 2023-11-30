package top.nextnet.cli;

public interface UserInterface {
    void showErrorMessage(String errorMessage);

    void showSuccessMessage(String s);

    String getCustomerFirstName();

    String getCustomerLastName();

    String getCustomerEmail();

    Booking getBookingFromOperator();
}
