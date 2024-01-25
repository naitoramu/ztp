import {Component, Input, OnInit} from '@angular/core';
import {FormControl} from "@angular/forms";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-input-error',
  standalone: true,
  imports: [
    NgIf,
  ],
  templateUrl: '../../view/input-error/input-error.component.html',
  styleUrl: '../../view/input-error/input-error.component.css'
})
export class InputErrorComponent implements OnInit {

  @Input()
  public control!: FormControl;

  public error!: string;

  ngOnInit(): void {
    this.error = this.formErrorMessage(this.control);
    this.control.statusChanges.subscribe((): void => {
      this.error = this.formErrorMessage(this.control)
    });
  }

  private formErrorMessage(control: FormControl): string {
    if (control.valid) {
      return '';
    }

    if (control.hasError('required')) {
      return 'Value required';
    }

    if (control.hasError('min')) {
      const requiredMinValue = control.errors!['min']['min'];
      return `The value must be greater than ${requiredMinValue}`;
    }

    if (control.hasError('maxlength')) {
      const requiredMaxLength = control.errors!['maxlength']['requiredLength'];
      return `The value cannot have more than ${requiredMaxLength} characters`;
    }

    return 'Invalid value';
  }
}
