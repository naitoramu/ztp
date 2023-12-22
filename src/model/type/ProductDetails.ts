export class ProductDetails {
    private _id: string;
    private _name: string;
    private _description: string;
    private _price: number;
    private _availableQuantity: number;

    constructor(id: string, name: string, description: string, price: number, availableQuantity: number) {
        this._id = id;
        this._name = name;
        this._description = description;
        this._price = price;
        this._availableQuantity = availableQuantity;
    }

    public get id(): string {
        return this._id;
    }

    public get name(): string {
        return this._name;
    }
    public set name(value: string) {
        this._name = value;
    }

    public get description(): string {
        return this._description;
    }
    public set description(value: string) {
        this._description = value;
    }
    
    public get price(): number {
        return this._price;
    }
    public set price(value: number) {
        this._price = value;
    }
    
    public get availableQuantity(): number {
        return this._availableQuantity;
    }
    public set availableQuantity(value: number) {
        this._availableQuantity = value;
    }
}