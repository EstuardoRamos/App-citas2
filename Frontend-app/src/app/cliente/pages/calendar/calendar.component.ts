import { Component, ChangeDetectionStrategy  } from '@angular/core';
import { CalendarEvent } from 'angular-calendar';
import { Subject } from 'rxjs';
import { startOfDay, endOfDay, addHours } from 'date-fns';

@Component({
  selector: 'app-calendar',
  templateUrl: './calendar.component.html',
  styleUrl: './calendar.component.css',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class CalendarComponent {
  viewDate: Date = new Date();

  refresh: Subject<any> = new Subject();

  events: CalendarEvent[] = [
    {
      start: addHours(startOfDay(new Date()), 9), // Example time 9:00 AM
      end: addHours(startOfDay(new Date()), 10), // Example time 10:00 AM
      title: 'Important Meeting',
      color: { primary: '#ff9800', secondary: '#ffcc80' },
      actions: [],
      allDay: false,
      resizable: {
        beforeStart: true,
        afterEnd: true,
      },
      draggable: true,
    },
    {
      start: addHours(startOfDay(new Date()), 10),
      end: addHours(startOfDay(new Date()), 11),
      title: 'Take cat to vet',
      color: { primary: '#ff9800', secondary: '#ffcc80' },
      actions: [],
      allDay: false,
      resizable: {
        beforeStart: true,
        afterEnd: true,
      },
      draggable: true,
    },
    {
      start: addHours(startOfDay(new Date()), 11),
      end: addHours(startOfDay(new Date()), 12),
      title: 'Daily Scrum',
      color: { primary: '#2196f3', secondary: '#bbdefb' },
      actions: [],
      allDay: false,
      resizable: {
        beforeStart: true,
        afterEnd: true,
      },
      draggable: true,
    },
  ];

  handleEvent(action: string, event: CalendarEvent): void {
    console.log(`${action}: ${event.title}`);
  }
}
