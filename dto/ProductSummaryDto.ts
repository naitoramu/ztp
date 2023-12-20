export class ProductSummaryDto {
    private _id: string;
    private _name: string;
    private _price: number;

    constructor(id: string, name: string, price: number) {
        this._id = id;
        this._name = name;
        this._price = price;
    }

    public get id(): string {
        return this._id;
    }

    public get name(): string {
        return this._name;
    }
    
    public get price(): number {
        return this._price;
    }
}