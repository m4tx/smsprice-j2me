/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package smspricepack;

import javax.microedition.lcdui.*;
import javax.microedition.midlet.*;

/**
 * @author m4tx
 */
public class SmsPrice extends MIDlet implements CommandListener {
    private Display display;
    private Form form = new Form("SmsPrice 1.0");
    private StringItem infoLabel = new StringItem("Wprowadź numer:", null);
    private TextField textField = new TextField(null, null, 32, TextField.DECIMAL);
    private StringItem stringItem = new StringItem("Cena: ", "brak danych");
    private StringItem authorLabel = new StringItem(" \nAutor: m4tx\n", "http://m4tx.pl/");
    private Command ok = new Command("OK", Command.SCREEN, 1);
    private Command exit = new Command("Wyjdź", Command.EXIT, 1);
    
    public SmsPrice() {
        display = Display.getDisplay(this);
        form.addCommand(exit);
        form.addCommand(ok);
        form.append(infoLabel);
        form.append(textField);
        form.append(stringItem);
        form.append(authorLabel);
        form.setCommandListener(this);
    }
    
    public void startApp() {
        display.setCurrent(form);
    }
    
    public void pauseApp() {
    }
    
    public void destroyApp(boolean unconditional) {
    }
    
    public void commandAction(Command command, Displayable displayable) {
        if (command == ok) {
            stringItem.setText(calculatePrice());
        } else if (command == exit) {
            destroyApp(false);
            notifyDestroyed();
        }
    }
    
    public String calculatePrice() {
        String number = textField.getString();
        if (number.length() < 2)
           return "brak danych";
        
        float price;
        int numberInt = Integer.parseInt(number.substring(0, 2));
        
        if (numberInt == 80)
            price = 0.0f;
        else if (numberInt == 70)
            price = 0.5f;
        else if (numberInt >= 71 && numberInt <= 79)
            price = numberInt - 70.0f;
        else {
            numberInt = Integer.parseInt(number.substring(0, 3));

            if (numberInt>=910 && numberInt<=925)
                price = numberInt - 900.0f;
            else price = -1.0f;
        }
        
        return (price == -1.0f) ? "brak danych" : Float.toString((price == 0.5f) ? 0.62f : price*1.23f) + "zł z VAT (" + Float.toString(price) + "zł bez VAT)";
    }
}