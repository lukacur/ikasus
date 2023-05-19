import { Component, HostListener, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs';
import { AuthService } from 'src/app/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit, OnDestroy {
  public getScreenWidth: number = window.innerWidth;
  public getScreenHeight: number = window.innerHeight;
  public isAuthenticated: boolean = false;
  public collapsed: boolean = true;
  public role: number | undefined;

  public user: { token: string } | undefined;
  private userSubscription: Subscription | undefined;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.userSubscription = this.authService.user.subscribe(user => {
      this.isAuthenticated = !!user;
      this.user = user;
      this.role = this.authService.decodeToken();
    });
  }

  ngOnDestroy() {
    this.userSubscription?.unsubscribe();
  }

  @HostListener('window:resize', ['$event'])
  onWindowResize() {
    this.getScreenWidth = window.innerWidth;
    this.getScreenHeight = window.innerHeight;
  }

  onLogout() {
    this.authService.logout();
  }
}
