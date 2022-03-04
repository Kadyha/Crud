import { Component, OnInit } from '@angular/core';
import { UserContact } from '../share/usercontact.model';
import { UsercontactService } from '../share/usercontact.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-usercontact',
  templateUrl: './usercontact.component.html',
  styleUrls: ['./usercontact.component.css'],
})

export class UsercontactComponent implements OnInit {

  usercontacts: UserContact[]; // Array<string>
  usercont: UserContact;

  constructor(private ucs: UsercontactService, private router: Router) {
  }

  editUserContact(usercontact: UserContact) {
    console.log(usercontact);
    localStorage.removeItem('editUserId');
    localStorage.setItem('editUserId', usercontact.id.toString());
    this.router.navigate(['actualizar']);
    this.ucs.update(usercontact);
  }

  deleteUserContact(usercontact: UserContact) {
    console.log(usercontact);
    localStorage.removeItem('editUserId');
    localStorage.setItem('editUserId', usercontact.id.toString());
    this.router.navigate(['eliminar']);
    console.log(usercontact);
    var r=confirm("¿Desea eliminar al usuario?");
    if (r==true)
      {
        this.ucs.delete(usercontact);
      }
    //this.ucs.delete(usercontact);
  }

  ngOnInit() {
    console.log('usercontact:init');
    this.usercontacts = this.ucs.getall();
    console.log(this.usercontacts);
  }
}
