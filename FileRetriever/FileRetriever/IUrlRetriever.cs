using System;
using System.Collections.Generic;

namespace FileRetriever { 

    interface IUrlRetriever {
        List<RowValue> retrieve(String contentToSearch, int queryCount);
    }
}
