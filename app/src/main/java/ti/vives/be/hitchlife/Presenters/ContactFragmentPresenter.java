package ti.vives.be.hitchlife.Presenters;

import java.util.List;

import ti.vives.be.hitchlife.Interfaces.ContactFragmentVP;
import ti.vives.be.hitchlife.Models.Repository.DataCallBack;
import ti.vives.be.hitchlife.Models.Repository.Entities.Contact;
import ti.vives.be.hitchlife.Models.Repository.Repository;

public class ContactFragmentPresenter implements ContactFragmentVP.Presenter {

    private ContactFragmentVP.View view;
    private Repository repository;

    public ContactFragmentPresenter(ContactFragmentVP.View view, Repository rep){
        this.view = view;
        this.repository = rep;
    }

    @Override
    public void getAllContacts() {
        //fill recyclerview with viewMethod --> danger if getalllogs takes too long, contactFragment oncreateview will create recyclerview without contactData --> TODO use callback
        List<Contact> list = repository.getAll();
        Contact[] contacts = list.toArray(new Contact[list.size()]);
        view.setContacts(contacts);
    }

    public void deleteContact(Contact contact){
        repository.deleteContactByid(contact.id);
    }
}
