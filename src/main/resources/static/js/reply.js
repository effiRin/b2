

async function getList({bno, page, size, goLast}){

    const result = await axios.get(`/replies/list/${bno}`, {params: {page, size}})

    if(goLast){
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))

        return getList({bno:bno, page:lastPage, size:size})

    }

    return result.data
}

async function addReply(replyObj) {
    const response = await axios.post(`/replies/`,replyObj)
    return response.data
}
// 원래 캐치 잡아줘야 하는데 앞쪽에서 잡아주니까 생략

async function getReply(rno) {
    const response = await axios.get(`/replies/${rno}`)
    return response.data
}

async function removeReply(rno) {
    const response = await axios.delete(`/replies/${rno}`)
    return response.data
}

