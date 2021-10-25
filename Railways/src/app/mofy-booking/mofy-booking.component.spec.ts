import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MofyBookingComponent } from './mofy-booking.component';

describe('MofyBookingComponent', () => {
  let component: MofyBookingComponent;
  let fixture: ComponentFixture<MofyBookingComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MofyBookingComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MofyBookingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
