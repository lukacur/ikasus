import { Component, HostListener, OnInit, OnDestroy } from '@angular/core';
import { Subscription, subscribeOn } from 'rxjs';
import { AuthService } from 'src/app/auth.service';
import { Notification, NotificationService } from 'src/app/notification.service';

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

  public notification: boolean = false;
  public notificationArray: Notification[] = []

  public user: { token: string } | undefined;
  private userSubscription: Subscription | undefined;

  constructor(private authService: AuthService, private notifServ: NotificationService) { }

  ngOnInit(): void {
    this.userSubscription = this.authService.user.subscribe(user => {
      this.isAuthenticated = !!user;
      this.user = user;
      this.role = this.authService.decodeToken();

      if(this.role && this.role == 0) {
        this.notifServ.getAllNotifications().subscribe(nts => {
          this.notificationArray = nts;
        })
      }
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
