import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionServidoresComponent } from './gestion-servidores.component';

describe('GestionServidoresComponent', () => {
  let component: GestionServidoresComponent;
  let fixture: ComponentFixture<GestionServidoresComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [GestionServidoresComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionServidoresComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
