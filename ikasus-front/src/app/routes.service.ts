import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class RoutesService {

  routes = [
    { login: "localhost/"}
  ]

  constructor() { }
}
