import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth.service';
import { Router } from '@angular/router';
import { FormGroup, NgForm, FormControl, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { Customer } from '../models/user.model';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  isLogin: boolean = true;
  error: string | undefined;
  loginForm!: FormGroup;

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit(): void {
    this.initForm();
  }

  initForm() {
    this.loginForm = new FormGroup({
      "username": new FormControl("", Validators.required),
      "password": new FormControl("", Validators.required),
      "type": new FormControl("", Validators.required),
    });
  }

  toggle() {
    this.isLogin = !this.isLogin;
  }

  onSubmit() {
    if (!this.loginForm.valid) return;

    const email = this.loginForm.get("username")!.value!;
    const password = this.loginForm.get("password")!.value!;
    const type = this.loginForm.get("type")!.value!;

    this.error = undefined;
    let authObservable: Observable<Customer>;
    authObservable = this.authService.login(email, password, Number(type));


    authObservable.subscribe({
      next: (respData) => {
        if (respData === null)
          throw new Error("Something went wrong.")
        else
          this.router.navigate(['/']);
      },
      error: (error) => {
        this.error = error.message;
      }
    });

    this.loginForm.reset();
  }

}
