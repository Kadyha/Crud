(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["main"],{

/***/ "./src/$$_lazy_route_resource lazy recursive":
/*!**********************************************************!*\
  !*** ./src/$$_lazy_route_resource lazy namespace object ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncaught exception popping up in devtools
	return Promise.resolve().then(function() {
		var e = new Error('Cannot find module "' + req + '".');
		e.code = 'MODULE_NOT_FOUND';
		throw e;
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./src/app/add-usercontact/add-usercontact.component.css":
/*!***************************************************************!*\
  !*** ./src/app/add-usercontact/add-usercontact.component.css ***!
  \***************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/add-usercontact/add-usercontact.component.html":
/*!****************************************************************!*\
  !*** ./src/app/add-usercontact/add-usercontact.component.html ***!
  \****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"col-md-6\">\n  <h2 class=\"text-center\">Add User Contact</h2>\n  <form [formGroup]=\"addForm\" (ngSubmit)=\"onSubmit()\">\n\n    <div class=\"form-group\">\n      <label for=\"firstname\">First Name:</label>\n      <input formControlName=\"firstname\" placeholder=\"First Name\" name=\"firstname\" class=\"form-control\" id=\"firstname\">\n      <div class=\"feedback-red\" *ngIf=\"isInvalid('firstname')\">\n        First Name is empty.\n      </div>\n    </div>\n\n    <div class=\"form-group\">\n      <label for=\"lastname\">Last Name:</label>\n      <input formControlName=\"lastname\" placeholder=\"Last name\" name=\"lastname\" class=\"form-control\" id=\"lastname\">\n      <div class=\"feedback-red\" *ngIf=\"isInvalid('lastname')\">\n        Last Name is empty.\n      </div>\n    </div>\n\n    <div class=\"form-group\">\n      <label for=\"email\">Email address:</label>\n      <input type=\"email\" formControlName=\"email\" placeholder=\"Email\" name=\"email\" class=\"form-control\" id=\"email\">\n      <div class=\"feedback-red\" *ngIf=\"isEmailInvalid('email')\">\n        Email is not valid.\n      </div>\n    </div>\n\n    <button button [disabled]=\"addForm.invalid\" class=\"btn btn-success\">Submit</button>\n    <button class=\"btn btn-danger ml-2 \" (click)=\"onCancel()\">Cancel</button>\n  </form>\n</div>\n"

/***/ }),

/***/ "./src/app/add-usercontact/add-usercontact.component.ts":
/*!**************************************************************!*\
  !*** ./src/app/add-usercontact/add-usercontact.component.ts ***!
  \**************************************************************/
/*! exports provided: AddUsercontactComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AddUsercontactComponent", function() { return AddUsercontactComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _share_usercontact_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../share/usercontact.service */ "./src/app/share/usercontact.service.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var AddUsercontactComponent = /** @class */ (function () {
    function AddUsercontactComponent(formBuilder, router, userService) {
        this.formBuilder = formBuilder;
        this.router = router;
        this.userService = userService;
        this.createUsercontact = new _angular_core__WEBPACK_IMPORTED_MODULE_0__["EventEmitter"]();
        this.emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    }
    AddUsercontactComponent.prototype.ngOnInit = function () {
        this.addForm = this.formBuilder.group({
            id: [],
            email: ['', [_angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].pattern(this.emailRegex)]],
            firstname: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            lastname: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required]
        });
    };
    AddUsercontactComponent.prototype.isInvalid = function (name) {
        var control = this.addForm.get(name);
        return control.invalid && control.dirty;
    };
    AddUsercontactComponent.prototype.isEmailInvalid = function (name) {
        var control = this.addForm.get(name);
        return control.invalid && control.dirty;
    };
    AddUsercontactComponent.prototype.onSubmit = function () {
        this.userService.create(this.addForm.value);
        this.router.navigate(['list']);
    };
    AddUsercontactComponent.prototype.onCancel = function () {
        this.router.navigate(['list']);
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Output"])(),
        __metadata("design:type", Object)
    ], AddUsercontactComponent.prototype, "createUsercontact", void 0);
    AddUsercontactComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-add-usercontact',
            template: __webpack_require__(/*! ./add-usercontact.component.html */ "./src/app/add-usercontact/add-usercontact.component.html"),
            styles: [__webpack_require__(/*! ./add-usercontact.component.css */ "./src/app/add-usercontact/add-usercontact.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"], _angular_router__WEBPACK_IMPORTED_MODULE_3__["Router"], _share_usercontact_service__WEBPACK_IMPORTED_MODULE_1__["UsercontactService"]])
    ], AddUsercontactComponent);
    return AddUsercontactComponent;
}());



/***/ }),

/***/ "./src/app/app.component.css":
/*!***********************************!*\
  !*** ./src/app/app.component.css ***!
  \***********************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/app.component.html":
/*!************************************!*\
  !*** ./src/app/app.component.html ***!
  \************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<!--The content below is only a placeholder and can be replaced.-->\n<div class=\"container\">\n  <h1>\n    <img width=\"100\" alt=\"Angular Logo\" src=\"data:image/svg+xml;base64,PHN2ZyB4bWxucz0iaHR0cDovL3d3dy53My5vcmcvMjAwMC9zdmciIHZpZXdCb3g9IjAgMCAyNTAgMjUwIj4KICAgIDxwYXRoIGZpbGw9IiNERDAwMzEiIGQ9Ik0xMjUgMzBMMzEuOSA2My4ybDE0LjIgMTIzLjFMMTI1IDIzMGw3OC45LTQzLjcgMTQuMi0xMjMuMXoiIC8+CiAgICA8cGF0aCBmaWxsPSIjQzMwMDJGIiBkPSJNMTI1IDMwdjIyLjItLjFWMjMwbDc4LjktNDMuNyAxNC4yLTEyMy4xTDEyNSAzMHoiIC8+CiAgICA8cGF0aCAgZmlsbD0iI0ZGRkZGRiIgZD0iTTEyNSA1Mi4xTDY2LjggMTgyLjZoMjEuN2wxMS43LTI5LjJoNDkuNGwxMS43IDI5LjJIMTgzTDEyNSA1Mi4xem0xNyA4My4zaC0zNGwxNy00MC45IDE3IDQwLjl6IiAvPgogIDwvc3ZnPg==\"> {{ title }}\n  </h1>\n\n  <!-- <app-dashboard></app-dashboard> -->\n\n</div>\n\n<router-outlet></router-outlet>\n"

/***/ }),

/***/ "./src/app/app.component.ts":
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/*! exports provided: AppComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppComponent", function() { return AppComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var AppComponent = /** @class */ (function () {
    function AppComponent() {
        this.title = 'Contacts App';
    }
    AppComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-root',
            template: __webpack_require__(/*! ./app.component.html */ "./src/app/app.component.html"),
            styles: [__webpack_require__(/*! ./app.component.css */ "./src/app/app.component.css")]
        })
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "./src/app/app.module.ts":
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/*! exports provided: AppModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppModule", function() { return AppModule; });
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/platform-browser */ "./node_modules/@angular/platform-browser/fesm5/platform-browser.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app.component */ "./src/app/app.component.ts");
/* harmony import */ var _add_usercontact_add_usercontact_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./add-usercontact/add-usercontact.component */ "./src/app/add-usercontact/add-usercontact.component.ts");
/* harmony import */ var _usercontact_usercontact_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./usercontact/usercontact.component */ "./src/app/usercontact/usercontact.component.ts");
/* harmony import */ var _dashboard_dashboard_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./dashboard/dashboard.component */ "./src/app/dashboard/dashboard.component.ts");
/* harmony import */ var _delete_delete_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./delete/delete.component */ "./src/app/delete/delete.component.ts");
/* harmony import */ var _app_routing_module__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./app.routing.module */ "./src/app/app.routing.module.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _edit_usercontact_edit_usercontact_component__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ./edit-usercontact/edit-usercontact.component */ "./src/app/edit-usercontact/edit-usercontact.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};










var AppModule = /** @class */ (function () {
    function AppModule() {
    }
    AppModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
            declarations: [
                _app_component__WEBPACK_IMPORTED_MODULE_2__["AppComponent"],
                _add_usercontact_add_usercontact_component__WEBPACK_IMPORTED_MODULE_3__["AddUsercontactComponent"],
                _usercontact_usercontact_component__WEBPACK_IMPORTED_MODULE_4__["UsercontactComponent"],
                _delete_delete_component__WEBPACK_IMPORTED_MODULE_6__["DeleteComponent"],
                _dashboard_dashboard_component__WEBPACK_IMPORTED_MODULE_5__["DashboardComponent"],
                _edit_usercontact_edit_usercontact_component__WEBPACK_IMPORTED_MODULE_9__["EditUsercontactComponent"]
            ],
            imports: [
                _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__["BrowserModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_8__["ReactiveFormsModule"],
                _app_routing_module__WEBPACK_IMPORTED_MODULE_7__["AppRoutingModule"]
            ],
            providers: [],
            bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_2__["AppComponent"]]
        })
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "./src/app/app.routing.module.ts":
/*!***************************************!*\
  !*** ./src/app/app.routing.module.ts ***!
  \***************************************/
/*! exports provided: AppRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppRoutingModule", function() { return AppRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _add_usercontact_add_usercontact_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./add-usercontact/add-usercontact.component */ "./src/app/add-usercontact/add-usercontact.component.ts");
/* harmony import */ var _edit_usercontact_edit_usercontact_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./edit-usercontact/edit-usercontact.component */ "./src/app/edit-usercontact/edit-usercontact.component.ts");
/* harmony import */ var _delete_delete_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./delete/delete.component */ "./src/app/delete/delete.component.ts");
/* harmony import */ var _dashboard_dashboard_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./dashboard/dashboard.component */ "./src/app/dashboard/dashboard.component.ts");
/* harmony import */ var _login_login_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./login/login.component */ "./src/app/login/login.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};







var routes = [
    { path: 'edit', component: _edit_usercontact_edit_usercontact_component__WEBPACK_IMPORTED_MODULE_3__["EditUsercontactComponent"] },
    { path: 'add', component: _add_usercontact_add_usercontact_component__WEBPACK_IMPORTED_MODULE_2__["AddUsercontactComponent"] },
    { path: 'delete', component: _delete_delete_component__WEBPACK_IMPORTED_MODULE_4__["DeleteComponent"] },
    { path: 'list', component: _dashboard_dashboard_component__WEBPACK_IMPORTED_MODULE_5__["DashboardComponent"] },
    { path: '', component: _login_login_component__WEBPACK_IMPORTED_MODULE_6__["LoginComponent"] }
];
var AppRoutingModule = /** @class */ (function () {
    function AppRoutingModule() {
    }
    AppRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forRoot(routes)],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]]
        })
    ], AppRoutingModule);
    return AppRoutingModule;
}());



/***/ }),

/***/ "./src/app/dashboard/dashboard.component.css":
/*!***************************************************!*\
  !*** ./src/app/dashboard/dashboard.component.css ***!
  \***************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/dashboard/dashboard.component.html":
/*!****************************************************!*\
  !*** ./src/app/dashboard/dashboard.component.html ***!
  \****************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<a [routerLink]=\"['/add']\" class=\"btn btn-success ml-2\">New</a>\n\n<hr>\n\n<app-usercontact></app-usercontact>\n"

/***/ }),

/***/ "./src/app/dashboard/dashboard.component.ts":
/*!**************************************************!*\
  !*** ./src/app/dashboard/dashboard.component.ts ***!
  \**************************************************/
/*! exports provided: DashboardComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DashboardComponent", function() { return DashboardComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var DashboardComponent = /** @class */ (function () {
    function DashboardComponent() {
    }
    DashboardComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-dashboard',
            template: __webpack_require__(/*! ./dashboard.component.html */ "./src/app/dashboard/dashboard.component.html"),
            styles: [__webpack_require__(/*! ./dashboard.component.css */ "./src/app/dashboard/dashboard.component.css")],
            changeDetection: _angular_core__WEBPACK_IMPORTED_MODULE_0__["ChangeDetectionStrategy"].OnPush
        })
    ], DashboardComponent);
    return DashboardComponent;
}());



/***/ }),

/***/ "./src/app/delete/delete.component.css":
/*!*********************************************!*\
  !*** ./src/app/delete/delete.component.css ***!
  \*********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/delete/delete.component.html":
/*!**********************************************!*\
  !*** ./src/app/delete/delete.component.html ***!
  \**********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<a [routerLink]=\"['/add']\" class=\"btn btn-success ml-2\">New</a>\n<hr>\n\n<app-usercontact></app-usercontact>\n"

/***/ }),

/***/ "./src/app/delete/delete.component.ts":
/*!********************************************!*\
  !*** ./src/app/delete/delete.component.ts ***!
  \********************************************/
/*! exports provided: DeleteComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DeleteComponent", function() { return DeleteComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var DeleteComponent = /** @class */ (function () {
    function DeleteComponent() {
    }
    DeleteComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-delete',
            template: __webpack_require__(/*! ./delete.component.html */ "./src/app/delete/delete.component.html"),
            styles: [__webpack_require__(/*! ./delete.component.css */ "./src/app/delete/delete.component.css")],
            changeDetection: _angular_core__WEBPACK_IMPORTED_MODULE_0__["ChangeDetectionStrategy"].OnPush
        })
    ], DeleteComponent);
    return DeleteComponent;
}());



/***/ }),

/***/ "./src/app/edit-usercontact/edit-usercontact.component.css":
/*!*****************************************************************!*\
  !*** ./src/app/edit-usercontact/edit-usercontact.component.css ***!
  \*****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/edit-usercontact/edit-usercontact.component.html":
/*!******************************************************************!*\
  !*** ./src/app/edit-usercontact/edit-usercontact.component.html ***!
  \******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"col-md-6\">\n  <h2 class=\"text-center\">Change User Contact</h2>\n  <form [formGroup]=\"addForm\" (ngSubmit)=\"onSubmit()\">\n\n    <div class=\"form-group\">\n      <label for=\"firstname\">First Name:</label>\n      <input formControlName=\"firstname\" placeholder=\"First Name\" name=\"firstname\" class=\"form-control\" id=\"firstname\">\n      <div class=\"feedback-red\" *ngIf=\"isInvalid('firstname')\">\n        First Name is empty.\n      </div>\n    </div>\n\n    <div class=\"form-group\">\n      <label for=\"lastname\">Last Name:</label>\n      <input formControlName=\"lastname\" placeholder=\"Last name\" name=\"lastname\" class=\"form-control\" id=\"lastname\">\n      <div class=\"feedback-red\" *ngIf=\"isInvalid('lastname')\">\n        Last Name is empty.\n      </div>\n    </div>\n\n    <div class=\"form-group\">\n      <label for=\"email\">Email address:</label>\n      <input type=\"email\" formControlName=\"email\" placeholder=\"Email\" name=\"email\" class=\"form-control\" id=\"email\">\n      <div class=\"feedback-red\" *ngIf=\"isEmailInvalid('email')\">\n        Email is not valid.\n      </div>\n    </div>\n\n    <button button [disabled]=\"addForm.invalid\" class=\"btn btn-success\">Submit</button>\n    <button class=\"btn btn-danger ml-2 \" (click)=\"onCancel()\">Cancel</button>\n  </form>\n</div>\n"

/***/ }),

/***/ "./src/app/edit-usercontact/edit-usercontact.component.ts":
/*!****************************************************************!*\
  !*** ./src/app/edit-usercontact/edit-usercontact.component.ts ***!
  \****************************************************************/
/*! exports provided: EditUsercontactComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "EditUsercontactComponent", function() { return EditUsercontactComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _share_usercontact_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../share/usercontact.service */ "./src/app/share/usercontact.service.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var EditUsercontactComponent = /** @class */ (function () {
    function EditUsercontactComponent(formBuilder, router, userService) {
        this.formBuilder = formBuilder;
        this.router = router;
        this.userService = userService;
        this.emailRegex = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    }
    EditUsercontactComponent.prototype.ngOnInit = function () {
        var userId = localStorage.getItem('editUserId');
        if (!userId) {
            alert('Invalid action.');
            this.router.navigate(['list']);
            return;
        }
        this.addForm = this.formBuilder.group({
            id: [],
            email: ['', [_angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required, _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].pattern(this.emailRegex)]],
            firstname: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required],
            lastname: ['', _angular_forms__WEBPACK_IMPORTED_MODULE_2__["Validators"].required]
        });
        var data = this.userService.getUserById(+userId);
        this.addForm.setValue(data);
    };
    EditUsercontactComponent.prototype.isInvalid = function (name) {
        var control = this.addForm.get(name);
        return control.invalid && control.dirty;
    };
    EditUsercontactComponent.prototype.isEmailInvalid = function (name) {
        var control = this.addForm.get(name);
        return control.invalid && control.dirty;
    };
    EditUsercontactComponent.prototype.onSubmit = function () {
        this.userService.update(this.addForm.value);
        this.router.navigate(['list']);
    };
    EditUsercontactComponent.prototype.onCancel = function () {
        this.router.navigate(['list']);
    };
    EditUsercontactComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-edit-usercontact',
            template: __webpack_require__(/*! ./edit-usercontact.component.html */ "./src/app/edit-usercontact/edit-usercontact.component.html"),
            styles: [__webpack_require__(/*! ./edit-usercontact.component.css */ "./src/app/edit-usercontact/edit-usercontact.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"], _angular_router__WEBPACK_IMPORTED_MODULE_3__["Router"], _share_usercontact_service__WEBPACK_IMPORTED_MODULE_1__["UsercontactService"]])
    ], EditUsercontactComponent);
    return EditUsercontactComponent;
}());



/***/ }),

/***/ "./src/app/login/login.component.css":
/*!*******************************************!*\
  !*** ./src/app/login/login.component.css ***!
  \*******************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/login/login.component.html":
/*!********************************************!*\
  !*** ./src/app/login/login.component.html ***!
  \********************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<a [routerLink]=\"['/add']\" class=\"btn btn-success ml-2\">New</a>\n\n<hr>\n\n<app-usercontact></app-usercontact>\n"

/***/ }),

/***/ "./src/app/login/login.component.ts":
/*!******************************************!*\
  !*** ./src/app/login/login.component.ts ***!
  \******************************************/
/*! exports provided: LoginComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "LoginComponent", function() { return LoginComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var LoginComponent = /** @class */ (function () {
    function LoginComponent() {
    }
    LoginComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-login',
            template: __webpack_require__(/*! ./login.component.html */ "./src/app/login/login.component.html"),
            styles: [__webpack_require__(/*! ./login.component.css */ "./src/app/login/login.component.css")],
            changeDetection: _angular_core__WEBPACK_IMPORTED_MODULE_0__["ChangeDetectionStrategy"].OnPush
        })
    ], LoginComponent);
    return LoginComponent;
}());



/***/ }),

/***/ "./src/app/share/usercontact.service.ts":
/*!**********************************************!*\
  !*** ./src/app/share/usercontact.service.ts ***!
  \**********************************************/
/*! exports provided: UsercontactService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UsercontactService", function() { return UsercontactService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var UsercontactService = /** @class */ (function () {
    function UsercontactService() {
        this.usercontacts = [{
                id: 0,
                firstname: 'Kadyha',
                lastname: 'Paz Gutierrez',
                email: 'kadyhapaz@gmail.com'
            }];
    }
    UsercontactService.prototype.create = function (usercontact) {
        var itemIndex = this.usercontacts.length;
        usercontact.id = this.getnextId();
        console.log(usercontact);
        this.usercontacts[itemIndex] = usercontact;
    };
    UsercontactService.prototype.delete = function (usercontact) {
        this.usercontacts.splice(this.usercontacts.indexOf(usercontact), 1);
    };
    UsercontactService.prototype.update = function (usercontact) {
        var itemIndex = this.usercontacts.findIndex(function (item) { return item.id === usercontact.id; });
        console.log(itemIndex);
        this.usercontacts[itemIndex] = usercontact;
    };
    UsercontactService.prototype.getall = function () {
        console.log('usercontactservice:getall');
        console.log(this.usercontacts);
        return this.usercontacts;
    };
    UsercontactService.prototype.reorderUserContacts = function (usercontact) {
        console.log('Zur Info:', usercontact);
        this.usercontacts = this.usercontacts
            .map(function (uc) { return uc.id === usercontact.id ? usercontact : uc; })
            .sort(function (a, uc) { return uc.id - uc.id; });
    };
    UsercontactService.prototype.getUserById = function (id) {
        console.log(id);
        var itemIndex = this.usercontacts.findIndex(function (item) { return item.id === id; });
        console.log(itemIndex);
        return this.usercontacts[itemIndex];
    };
    UsercontactService.prototype.getnextId = function () {
        var highest = 0;
        this.usercontacts.forEach(function (item) {
            if (highest === 0) {
                highest = item.id;
            }
            if (highest < item.id) {
                highest = item.id;
            }
        });
        return highest + 1;
    };
    UsercontactService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        })
    ], UsercontactService);
    return UsercontactService;
}());



/***/ }),

/***/ "./src/app/usercontact/usercontact.component.css":
/*!*******************************************************!*\
  !*** ./src/app/usercontact/usercontact.component.css ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ""

/***/ }),

/***/ "./src/app/usercontact/usercontact.component.html":
/*!********************************************************!*\
  !*** ./src/app/usercontact/usercontact.component.html ***!
  \********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"usercontact-list\">\n  <table class=\"table table-bordered\">\n    <thead>\n      <tr>\n        <th>Id</th>\n        <th>Firstname</th>\n        <th>Lastname</th>\n        <th>Email</th>\n        <th>Edit</th>\n        <th>Delete</th>\n      </tr>\n    </thead>\n    <tbody>\n      <tr *ngFor=\"let usercon of usercontacts\">\n        <td>{{ usercon.id }}</td>\n        <td>{{ usercon.firstname }}</td>\n        <td>{{ usercon.lastname }}</td>\n        <td>{{ usercon.email }}</td>\n        <td>\n          <button class=\" btn btn-primary \" (click)=\"editUserContact(usercon) \">Update</button>\n        </td>\n        <td>\n          <button class=\"btn btn-danger ml-2 \" (click)=\"deleteUserContact(usercon) \">Delete</button>\n        </td>\n      </tr>\n\n\n    </tbody>\n  </table>\n</div>\n"

/***/ }),

/***/ "./src/app/usercontact/usercontact.component.ts":
/*!******************************************************!*\
  !*** ./src/app/usercontact/usercontact.component.ts ***!
  \******************************************************/
/*! exports provided: UsercontactComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UsercontactComponent", function() { return UsercontactComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _share_usercontact_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../share/usercontact.service */ "./src/app/share/usercontact.service.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var UsercontactComponent = /** @class */ (function () {
    function UsercontactComponent(ucs, router) {
        this.ucs = ucs;
        this.router = router;
    }
    UsercontactComponent.prototype.editUserContact = function (usercontact) {
        console.log(usercontact);
        localStorage.removeItem('editUserId');
        localStorage.setItem('editUserId', usercontact.id.toString());
        this.router.navigate(['edit']);
        this.ucs.update(usercontact);
    };
    UsercontactComponent.prototype.deleteUserContact = function (usercontact) {
        console.log(usercontact);
        localStorage.removeItem('editUserId');
        localStorage.setItem('editUserId', usercontact.id.toString());
        this.router.navigate(['delete']);
        console.log(usercontact);
        var r = confirm("¿Desea eliminar al usuario?");
        if (r == true) {
            this.ucs.delete(usercontact);
        }
        //this.ucs.delete(usercontact);
    };
    UsercontactComponent.prototype.ngOnInit = function () {
        console.log('usercontact:init');
        this.usercontacts = this.ucs.getall();
        console.log(this.usercontacts);
    };
    UsercontactComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-usercontact',
            template: __webpack_require__(/*! ./usercontact.component.html */ "./src/app/usercontact/usercontact.component.html"),
            styles: [__webpack_require__(/*! ./usercontact.component.css */ "./src/app/usercontact/usercontact.component.css")],
        }),
        __metadata("design:paramtypes", [_share_usercontact_service__WEBPACK_IMPORTED_MODULE_1__["UsercontactService"], _angular_router__WEBPACK_IMPORTED_MODULE_2__["Router"]])
    ], UsercontactComponent);
    return UsercontactComponent;
}());



/***/ }),

/***/ "./src/environments/environment.ts":
/*!*****************************************!*\
  !*** ./src/environments/environment.ts ***!
  \*****************************************/
/*! exports provided: environment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "environment", function() { return environment; });
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
var environment = {
    production: false
};
/*
 * In development mode, to ignore zone related error stack frames such as
 * `zone.run`, `zoneDelegate.invokeTask` for easier debugging, you can
 * import the following file, but please comment it out in production mode
 * because it will have performance impact when throw error
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.


/***/ }),

/***/ "./src/main.ts":
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser-dynamic */ "./node_modules/@angular/platform-browser-dynamic/fesm5/platform-browser-dynamic.js");
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app/app.module */ "./src/app/app.module.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./environments/environment */ "./src/environments/environment.ts");




if (_environments_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].production) {
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["enableProdMode"])();
}
Object(_angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__["platformBrowserDynamic"])().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_2__["AppModule"])
    .catch(function (err) { return console.log(err); });


/***/ }),

/***/ 0:
/*!***************************!*\
  !*** multi ./src/main.ts ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! /home/kadyha/Angular6-UserContact-App/src/main.ts */"./src/main.ts");


/***/ })

},[[0,"runtime","vendor"]]]);
//# sourceMappingURL=main.js.map