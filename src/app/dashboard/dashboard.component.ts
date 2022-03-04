import { Component, ChangeDetectionStrategy } from '@angular/core';
import { UserContact } from '../share/usercontact.model';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush
})
export class DashboardComponent {

}
