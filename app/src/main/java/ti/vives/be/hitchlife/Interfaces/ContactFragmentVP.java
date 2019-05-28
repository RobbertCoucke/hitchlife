package ti.vives.be.hitchlife.Interfaces;

import ti.vives.be.hitchlife.Models.Repository.DataCallBack;
import ti.vives.be.hitchlife.Models.Repository.Entities.Contact;

public interface ContactFragmentVP {
    interface View{
        void setContacts(Contact[] contacts);

    }
    interface Presenter{

        void getAllContacts();
        void deleteContact(Contact contact);
    }
}
