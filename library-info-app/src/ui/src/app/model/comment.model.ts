export class Comment {

    constructor(
        public id: string,
        public text: string,
        public timestamp: Date,
        public bookId?: string) {
    }
}
