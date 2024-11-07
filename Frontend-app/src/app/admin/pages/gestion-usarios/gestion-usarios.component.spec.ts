import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GestionUsariosComponent } from './gestion-usarios.component';

describe('GestionUsariosComponent', () => {
  let component: GestionUsariosComponent;
  let fixture: ComponentFixture<GestionUsariosComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GestionUsariosComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GestionUsariosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
