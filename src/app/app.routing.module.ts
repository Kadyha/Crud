import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AddUsercontactComponent } from './add-usercontact/add-usercontact.component';
import { EditUsercontactComponent } from './edit-usercontact/edit-usercontact.component';
import { DeleteComponent } from './delete/delete.component';
import { DashboardComponent } from './dashboard/dashboard.component';

const routes: Routes = [
  { path: 'actualizar', component: EditUsercontactComponent },
  { path: 'registrar', component: AddUsercontactComponent },
  { path: 'eliminar', component: DeleteComponent },
  { path: '', component: DashboardComponent },
  { path: 'consultar', component: DashboardComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
