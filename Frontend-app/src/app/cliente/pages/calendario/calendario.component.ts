import { Component } from '@angular/core';

@Component({
  selector: 'app-calendario',
  templateUrl: './calendario.component.html',
  styleUrl: './calendario.component.css'
})
export class CalendarioComponent {
  staffList = [
    { name: 'Rob', events: [1] },
    { name: 'Mike', events: [1] },
    { name: 'Kate', events: [1] },
    { name: 'Lisa', events: [1] },
    { name: 'Arnold', events: [1] },
    { name: 'Lee', events: [1] },
    { name: 'Jong', events: [1] }
  ];

  timeSlots = Array.from({ length: 24 }, (_, i) => `${i}:00`);

  events = [
    { name: 'Important Meeting', startHour: 8, endHour: 9, color: 'orange' },
    { name: 'Daily Scrum', startHour: 10, endHour: 11, color: 'blue' },
    { name: 'Customer Meeting', startHour: 12, endHour: 13, color: 'green' },
    { name: 'Sales Forecast Meeting', startHour: 14, endHour: 16, color: 'blue' }
  ];

  // Método para agendar una nueva cita
  scheduleAppointment(time: string) {
    const startHour = parseInt(time);
    const newEvent = {
      name: 'New Appointment',
      startHour: startHour,
      endHour: startHour + 1,
      color: 'green'
    };
    this.events.push(newEvent);
  }
}
