import { Component, OnInit, EventEmitter, Output } from '@angular/core';
import { UserContact } from '../share/usercontact.model';
import { UsercontactService } from '../share/usercontact.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { Router } from '@angular/router';


@Component({
  selector: 'app-edit-usercontact',
  templateUrl: './edit-usercontact.component.html',
  styleUrls: ['./edit-usercontact.component.css']
})
export class EditUsercontactComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private router: Router, private userService: UsercontactService) { }
  addForm: FormGroup;
  usercontact: UserContact;

  emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;

  ngOnInit() {
    const userId = localStorage.getItem('editUserId');
    if (!userId) {
      alert('Invalid action.');
      this.router.navigate(['consultar']);
      return;
    }
    this.addForm = this.formBuilder.group({
      id: [],
      email: ['', [Validators.required, Validators.pattern(this.emailRegex)]],
      firstname: ['', Validators.required],
      lastname: ['', Validators.required]
    });
    const data = this.userService.getUserById(+userId);
    this.addForm.setValue(data);
  }

  isInvalid(name: string) {
    const control = this.addForm.get(name);
    return control.invalid && control.dirty;
  }

  isEmailInvalid(name: string) {
    const control = this.addForm.get(name);
    return control.invalid && control.dirty;
  }

  onSubmit() {
    this.userService.update(this.addForm.value);
    this.router.navigate(['consultar']);
  }

  onCancel() {
    this.router.navigate(['consultar']);
  }
}
