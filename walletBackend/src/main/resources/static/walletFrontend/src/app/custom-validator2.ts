import { ValidationErrors, ValidatorFn, AbstractControl } from "@angular/forms";

export class CustomValidator2 {
    static patternValidator(regex: RegExp, error: ValidationErrors):ValidatorFn{
        return (control: AbstractControl):{[key: string]:any}=>{
          if(!control.value){
              //if control is empty return no error
              return null;
          }
  
          // test the value of the control against the regexp supplied
          const valid=regex.test(control.value);
  
          //if true, return error, else return no error passed in the second parameter
          return valid? error:null;
        };  
      }
}
