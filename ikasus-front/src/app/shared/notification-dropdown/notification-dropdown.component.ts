import { Subscription } from 'rxjs';
import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { Notification, NotificationService } from 'src/app/notification.service';

@Component({
  selector: 'app-notification-dropdown',
  templateUrl: './notification-dropdown.component.html',
  styleUrls: ['./notification-dropdown.component.scss']
})
export class NotificationDropdownComponent implements OnInit, OnDestroy {
  notifications: Notification[] | undefined;
  sub: Subscription = new Subscription();

  constructor(private serv: NotificationService) { }

  ngOnInit(): void {
    this.getData()
  }

  markSeen(n: Notification) {
    this.serv.markSeen(n.id, n.rentalId).subscribe(_ => {
      this.getData();
    })
  }

  delete(n: Notification) {
    this.serv.deleteNotification(n.id, n.rentalId).subscribe(_ => {
      this.getData();
    })
  }

  getData() {
    this.sub.add(
      this.serv.getAllNotifications().subscribe(ntf => {
        this.notifications = ntf;
      })
    )
  }

  ngOnDestroy(): void {
    if (this.sub)
      this.sub.unsubscribe()
  }
}
