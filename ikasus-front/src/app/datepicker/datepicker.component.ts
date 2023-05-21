import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-datepicker',
  templateUrl: './datepicker.component.html',
  styleUrls: ['./datepicker.component.scss']
})
export class DatepickerComponent implements OnInit {
  form!: FormGroup;

  constructor(private router: Router) { }

  ngOnInit(): void {
    this.form = new FormGroup({
      "from": new FormControl("", Validators.required),
      "to": new FormControl("", Validators.required),
      "maxPricePerDay": new FormControl(null),
    })
  }

  submit() {
    if (this.form && this.form.valid) {
      this.router.navigate(["rent"], { queryParams: {
        from: this.form.get("from")?.value,
        to: this.form.get("to")?.value,
        price: this.form.get("maxPricePerDay")?.value
      }
      })
    }
  }
}
