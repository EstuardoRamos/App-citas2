import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogoServicioComponent } from './dialogo-servicio.component';

describe('DialogoServicioComponent', () => {
  let component: DialogoServicioComponent;
  let fixture: ComponentFixture<DialogoServicioComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DialogoServicioComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DialogoServicioComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
