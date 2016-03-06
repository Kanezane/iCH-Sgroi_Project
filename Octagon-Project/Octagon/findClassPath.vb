Public Class findClassPath
    Public Function find() As String
        Dim binFolder() As String = Split(My.Application.Info.DirectoryPath.ToString, "\")
        Dim classPath As String = ""
        For cont = 0 To binFolder.Count - 3
            If cont = 0 Then
                classPath = classPath & binFolder(cont)
            Else
                classPath = classPath & "\" & binFolder(cont)
            End If
        Next
        Return classPath
    End Function
End Class

