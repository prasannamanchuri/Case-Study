export class TrainInfo {
    constructor(public _id:string,public trainname:string,public from:string,public to:string,public date:Date,
        public class_a_seats:Number,public class_b_seats:Number,public class_c_seats:Number,
        public class_a_amount:Number,public class_b_amount:Number,public class_c_amount:Number,
        public status:string) {
        
    }
}