import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EstadoCitaDialogComponent } from './estado-cita-dialog.component';

describe('EstadoCitaDialogComponent', () => {
  let component: EstadoCitaDialogComponent;
  let fixture: ComponentFixture<EstadoCitaDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [EstadoCitaDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(EstadoCitaDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
