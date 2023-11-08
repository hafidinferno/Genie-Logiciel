package fr.univ_lyon1.info.m1.elizagpt.controleur;

import fr.univ_lyon1.info.m1.elizagpt.model.Dao;
import fr.univ_lyon1.info.m1.elizagpt.model.Ia;
import fr.univ_lyon1.info.m1.elizagpt.model.HandleMessage;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageProcessor;
import fr.univ_lyon1.info.m1.elizagpt.model.MessageId;

public class Controleur {
    /** le controlleur va nous permettre de gérer les différentes actions entre la vue et le model .*/


   private final Dao userMessage;
   private HandleMessage handleMessage;
   private final Ia ia;
   private final int userId ;
   private final

   public Controleur(Dao userMessage,HandleMessage handleMessage Ia ia, int userId){
       this.userMessage=userMessage;
       this.handleMessage=handleMessage;
       this.ia=ia;
       this.userId=userId;
   }

    public Ia getIa() {
        return ia;
    }

    public HandleMessage getHandleMessage() {
        return handleMessage;
    }



    public Dao getUserMessage() {
        return userMessage;
    }

    public int getUserId() {
        return userId;
    }

    public void setIa(Ia ia){
       this.ia=ia;
    }

    public void setUserMessage(Dao userMessage){
       this.userMessage=userMessage;
    }

    public void setUserId(int userId){
       this.userId=userId;
    }

    public void setHandleMessage(HandleMessage handleMessage) {
        this.handleMessage = handleMessage;
    }

    public void sendMessage(String Message){
             userMessage.addMessage(Message,userId);
    }


    public void replyMessage(Dao Message){
             handleMessage.reply(message, userId);
    }


















}
