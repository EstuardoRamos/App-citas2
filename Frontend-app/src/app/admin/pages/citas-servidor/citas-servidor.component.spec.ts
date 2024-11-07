import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CitasServidorComponent } from './citas-servidor.component';

describe('CitasServidorComponent', () => {
  let component: CitasServidorComponent;
  let fixture: ComponentFixture<CitasServidorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CitasServidorComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CitasServidorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
